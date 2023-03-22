package com.kaju.excel.parsers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.EntityData;
import models.FieldData;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class JsonParser {

    private static void traverseAllNodes(JsonNode jsonNode, String currentNode, List<EntityData> entityDataList) {
        if (!jsonNode.isValueNode()) {
            Iterator<Map.Entry<String, JsonNode>> fieldIterator = jsonNode.fields();
            Iterator<JsonNode> elementsIterator = jsonNode.elements();
            List<String> nodesLst = new ArrayList<>();
            String fieldHeaders = "";
            String fieldValues = "";
            EntityData newEntity = new EntityData();
            while (fieldIterator.hasNext()) {
                Map.Entry<String, JsonNode> fieldObj = fieldIterator.next();
                if (!fieldObj.getValue().isValueNode()) {
                    String node = fieldObj.getKey();
                    System.out.println("???"+fieldObj.getValue());
                    nodesLst.add(node);
                } else {
                    FieldData fieldData = new FieldData();
                    fieldData.setFieldName(fieldObj.getValue().asText());
                    fieldData.setFieldHeaderName(fieldObj.getKey());
                    newEntity.getFieldsList().add(fieldData);
                    fieldHeaders += fieldObj.getKey() + ",";
                    fieldValues += fieldObj.getValue() + ",";

                }
            }
            if (currentNode != null) {
                int depth=currentNode.split("->").length-1;
                String name=findCurrentNodeName(currentNode);
                newEntity.setEntityName(name+depth);
                newEntity.setDisplayName(name);
                String parentNodeName = findParentNodeName(currentNode);
                Optional<EntityData> entityDataOptional = findEntity(entityDataList, parentNodeName);
                newEntity.setDepth(depth);
                if (entityDataOptional.isPresent()) {
                    boolean isNodePresent = checkNodeListHasEntity(entityDataOptional.get(), newEntity.getEntityName());
                    if (!entityDataOptional.get().getEntityName().equals(newEntity.getEntityName()) && (!isNodePresent)) {
                        entityDataOptional.get().getEntityDataList().add(newEntity);
                    }
                    if (isNodePresent && !fieldHeaders.isEmpty()) {
                        entityDataOptional.get().getEntityDataList().add(newEntity);
                    }
                } else {
                    entityDataList.add(newEntity);
                }
                System.out.println(currentNode + "\n field Keys " + fieldHeaders + "\n field values " + fieldValues);
            }

            int index = 0;
            while (elementsIterator.hasNext()) {
                JsonNode nextNode = elementsIterator.next();

                if (!nextNode.isValueNode()) {
                    String currNode = "";
                    if (currentNode != null) {
                        currNode = currentNode;
                    }
                    if (index < nodesLst.size()) {
                        currNode += "->" + nodesLst.get(index);
                    }
                    traverseAllNodes(nextNode, currNode, entityDataList);
                    index++;
                }
            }

        } else {
            System.out.println("## " + jsonNode.asText());
        }
    }

    private static boolean checkNodeListHasEntity(EntityData entityData, String entityName) {
        return entityData.getEntityDataList().stream().filter(entity -> entity.getEntityName().equals(entityName)).findFirst().isPresent();
    }

    private static Optional<EntityData> findEntity(List<EntityData> entityDataList, String entityName) {

        AtomicReference<Optional<EntityData>> entityDataOptional = new AtomicReference<>(entityDataList.stream().filter(entityData -> {
            return entityData.getEntityName().equals(entityName);
        }).findFirst());
        if (!entityDataOptional.get().isPresent()) {
            entityDataList.forEach(entityData -> {
                        entityDataOptional.set(findEntity(entityData.getEntityDataList(), entityName));

                    }
            );
        }
        return entityDataOptional.get();
    }

    private static String findParentNodeName(String entityName) {
        String splitStrings[] = entityName.split("->");
        if (splitStrings.length > 2) {
            return splitStrings[splitStrings.length - 2];
        } else {
            if (splitStrings.length>1) {
                return splitStrings[1];
            } else {
                return entityName;
            }
        }
    }

    private static String findCurrentNodeName(String entityName) {
        String splitStrings[] = entityName.split("->");
        if (splitStrings.length > 2) {
            return splitStrings[splitStrings.length - 1];
        } else {
            if (splitStrings.length>1) {
                return splitStrings[1];
            } else {
                return entityName;
            }
        }
    }


    public static List<EntityData> parseJsonContent(String content) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        //File jsonInputFile = new File(fileName);
        JsonNode jsonNode = objectMapper.readTree(content);
        List<EntityData> entityDataList = new ArrayList<>();
        traverseAllNodes(jsonNode, null, entityDataList);
        return entityDataList;
    }

    public static List<EntityData> parse(String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try(InputStream in=Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            String content=new String((in.readAllBytes()));
            //     File jsonInputFile = new File(fileName);
            JsonNode jsonNode = objectMapper.readTree(content);
            List<EntityData> entityDataList = new ArrayList<>();
            traverseAllNodes(jsonNode, null, entityDataList);
            return entityDataList;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
}
