
import com.kaju.excel.parsers.JsonParser;
import models.EntityData;

import java.io.IOException;
import java.util.List;


public class ExcelUtil {

    public static void generateExel(String jsonContent) throws IOException {

        List<EntityData> entityDataList= JsonParser.parseJsonContent(jsonContent);
        ExcelGenerator.createExcelFile("test.xlsx",entityDataList);

    }

    public static void main(String args[]) throws IOException {

        List<EntityData> entityDataList= JsonParser.parse("sampleProjectMetaData.json");
        ExcelGenerator.createExcelFile("",entityDataList);

    }


}
