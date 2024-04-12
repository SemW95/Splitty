package client.scenes;

import client.utils.ScreenUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.Expense;
import commons.Tag;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;


/**
 * Statistics popup.
 */
public class StatisticsCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private final ServerUtils server;
    private ResourceBundle resources;
    private Event event;
    @FXML
    private Pane root;
    @FXML
    private PieChart pieChart;
    @FXML
    private Label totalCost;

    @Inject
    public StatisticsCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        root.addEventFilter(KeyEvent.KEY_PRESSED,
            ScreenUtils.exitHandler(resources, mainCtrl::closePopup));
    }

    private void populate() {
        if (event == null) {
            return;
        }

        String spent = (char) 8364 + event.totalAmountSpent().toPlainString();
        totalCost.setText(spent);

        Map<Tag, BigDecimal> sums = new HashMap<>();
        for (Expense expense : event.getExpenses()) {
            if (expense.getTag() != null && expense.getPaid() != null) {
                sums.merge(expense.getTag(), expense.getPaid(), BigDecimal::add);
            }
        }
        pieChart.getData().clear();
        pieChart.layout();
        for (Tag tag : event.getTags()) {
            BigDecimal amount = sums.getOrDefault(tag, BigDecimal.ZERO);
            pieChart.getData().add(new PieChart.Data(tag.getName(), amount.doubleValue()));
        }
        for (final PieChart.Data data : pieChart.getData()) {
            Tag tag =
                event.getTags().stream().filter(t -> t.getName().equals(data.getName())).findFirst()
                    .get();

            data.getNode().setStyle("-fx-pie-color: " + tag.getColour().toHexString());
        }
        Set<Node> items = pieChart.lookupAll(".chart-legend-item");
        for (Node item : items) {
            Label itemLabel = (Label) item;
            DecimalFormat df = new DecimalFormat("#.##");
            Tag tag =
                event.getTags().stream().filter(t -> t.getName().equals(itemLabel.getText()))
                    .findFirst()
                    .get();
            double amount = sums.getOrDefault(tag, BigDecimal.ZERO).doubleValue();
            String percentage =
                df.format(100 * amount / event.totalAmountSpent().doubleValue()) + "%";
            itemLabel.setText(tag.getName() + " " + (char) 8364 + amount + ", " + percentage);
            itemLabel.getGraphic().setStyle("-fx-pie-color: " + tag.getColour().toHexString());
        }
    }

    /**
     * Sets the current event to the provided one and populates the screen.
     *
     * @param event the event to show
     */
    public void update(Event event) {
        this.event = event;
        populate();
    }
}