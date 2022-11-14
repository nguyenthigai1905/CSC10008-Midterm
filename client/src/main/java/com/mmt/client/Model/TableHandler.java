package com.mmt.client.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TableHandler implements Runnable {
    private final TableView<String> table;

    public TableHandler(TableView<String> newTable) {
        this.table = newTable;
    }

    @Override
    public void run() {
        List<String> data = createData();

        /*
        Author: Dustin Redmond
        Link: https://gist.github.com/dustinkredmond/4481d42011286084bff707bd15265c3d
         */

        // get header from data
        String[] header = data.get(0).split(",");

        // for each header, create a tableColumn
        for (String column : header) {
            TableColumn<String, String> tableColumn = new TableColumn<>(column);
            this.table.getColumns().add(tableColumn);

            tableColumn.setCellValueFactory(cellDataFeatures -> {
                String value = cellDataFeatures.getValue();
                String[] cells = value.split(",");
                int idx = cellDataFeatures.getTableView().getColumns().indexOf(cellDataFeatures.getTableColumn());
                if (idx >= cells.length) {
                    return new SimpleStringProperty("");
                } else {
                    return new SimpleStringProperty(cells[idx]);
                }
            });

            this.table.setItems(FXCollections.observableArrayList(data));
            this.table.getItems().remove(0);
        }
    }

    private List<String> createData() {
        List<String> data = new ArrayList<>();

        String msg = null;
        while (true) {
            try {
                msg = ClientModel.getInput().readUTF();
                if (msg.equals("stop")) break;
                data.add(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        data.remove(0);
        return data;
    }
}
