package com.mmt.client.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TableHandlerModel implements Runnable {
    private final TableView<String> table;

    public TableHandlerModel(TableView<String> newTable) {
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

        // create row to check if table is empty or not
        ObservableList<String> row = table.getItems();
        if (row == null) {
            table.getItems().clear();
        }

        // for each header, create a tableColumn
        for (String column : header) {

            TableColumn<String, String> tableColumn = new TableColumn<>(column);

            if (this.table.getColumns().isEmpty() || this.table.getColumns().size() != header.length) {
                this.table.getColumns().add(tableColumn);
            }

            tableColumn.setCellValueFactory(cellDataFeatures -> {
                String value = cellDataFeatures.getValue();
                String[] cells = value.split(",");
                int idx = cellDataFeatures.getTableView().getColumns().indexOf(cellDataFeatures.getTableColumn());
                if (idx >= cells.length) {
                    return new SimpleStringProperty("");
                } else {
                    return new SimpleStringProperty(cells[idx].substring(1, cells[idx].length() - 1));
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
