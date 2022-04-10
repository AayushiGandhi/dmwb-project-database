package crud;

import Logs.LogWriter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeleteRow {
    public void delete_row(String query, String currentDatabase, int databaseIndex, List<DataBase> listOfdatabase){
        HashMap<String, ArrayList<String>> deleteMap = new HashMap<>();
        //DELETE FROM movies WHERE movie_id = 18;
        String[] splitStr = query.split("\\s+");
        String tableName = splitStr[2];
        String columnName = splitStr[4];
        int finalDelete=0;
        int count=0;

        for (int i = 0; i < listOfdatabase.get(databaseIndex).getListOfTables().size(); i++) {
            System.out.println(" --------- hi       " + listOfdatabase.get(databaseIndex).getListOfTables().get(i).getTableName());
            if ((tableName + ".txt").equalsIgnoreCase(listOfdatabase.get(databaseIndex).getListOfTables().get(i).getTableName())) {
                count=i;
                System.out.println("------------ " + tableName);
                deleteMap = listOfdatabase.get(databaseIndex).getListOfTables().get(i).getTableData();
                List<String> deleteElements = new ArrayList<>();
                deleteElements = deleteMap.get(columnName);
                for(int finIndexForDelete =0 ; finIndexForDelete<deleteElements.size();finIndexForDelete++){
                    if(deleteElements.equals(deleteElements.get(finIndexForDelete))){
                        finalDelete=finIndexForDelete;
                    }
                }
            }

        }

        if(finalDelete > -1){

        boolean flag=false;
        for (int l = 0; l < listOfdatabase.get(databaseIndex).getListOfTables().get(count).getTableData().values().size(); l++) {
            for (String s : listOfdatabase.get(databaseIndex).getListOfTables().get(count).getTableData().keySet()) {
                listOfdatabase.get(databaseIndex).getListOfTables().get(count).getTableData().get(s).remove(finalDelete);
                flag=true;
            }
            if(flag){
                break;
            }
        }
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            LogWriter.login_time = dtf.format(now);
            LogWriter.writeQueryLogStatus("DELETE :",query,true);


            LogWriter.writeEventLogStatus("DELETED records from  :",query);


        }
        else{
            LogWriter.writeQueryLogStatus("DELETE :",query,false);
        }

    }
}
