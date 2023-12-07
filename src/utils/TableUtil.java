package utils;

import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public class TableUtil
{
    public static void autoResizeColumns(TableView<?> table)
    {
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.getColumns().stream().forEach((column) -> {
            Text t = new Text(column.getText());
            double max = t.getLayoutBounds().getWidth();
            for(int i = 0; i < table.getItems().size(); i++)
            {
                if(column.getCellData(i) != null)
                {
                    t = new Text(column.getCellData(i).toString());
                    double calcWidth = t.getLayoutBounds().getWidth();
                    if(calcWidth > max)
                    {
                        max = calcWidth;
                    }
                }
            }
            column.setPrefWidth(max + 50.0d);
        });
    }
}
