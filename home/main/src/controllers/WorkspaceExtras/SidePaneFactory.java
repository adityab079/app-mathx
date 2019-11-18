package main.src.controllers.WorkspaceExtras;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import main.src.controllers.Listeners.SandBoxListeners;
import main.src.controllers.Listeners.SidePaneListeners;
import main.src.controllers.Operator.*;

/**
 * @author Karandeep Singh Grewal
 */


public class SidePaneFactory {

    public void addLabelToSidePane(VBox sidePane, String labelName) {
        ParentOperator operator = null;
        switch (labelName) {
            case "Unary": {
                operator = new UnaryOperator();
                break;
            }
            case "Binary": {
                operator = new BinaryOperator();
                break;
            }
            case "Number": {
                operator = new NumberOperator();
                break;
            }
            case "Counter": {
                operator = new CounterOperator();
                break;
            }
        }
        assert operator != null;
        Pane label = operator.produceLabel();
        sidePane.getChildren().addAll(label);
    }

    public void addOperatorToSidePane(Pane sandBox, VBox sidePane, String string, String operatorType, int grade, StackPane commonPane) {
        SidePaneListeners sidePaneListeners = new SidePaneListeners();

        ParentOperator operator;
        switch (operatorType) {
            case "Unary": {
                operator = new UnaryOperator();
                break;
            }
            case "Binary": {
                operator = new BinaryOperator();
                break;
            }
            case "Number": {
                operator = new NumberOperator();
                break;
            }
            case "Counter": {
                operator = new CounterOperator();
                break;
            }
            default:
                operator = new BinaryOperator();
        }

        StackPane stackPane;
        stackPane = operator.produceOperator(string);
        sidePane.getChildren().addAll(stackPane);

        stackPane.setOnMouseClicked(e -> {
            SandBoxListeners sandBoxListeners = new SandBoxListeners();
            StackPane newStackPane;
            newStackPane = new StackPane(operator.produceOperator(string));
            sandBoxListeners.makeDraggable(newStackPane, commonPane);
            sandBoxListeners.makeDeletable(newStackPane);
            //This value x in (grade>x) is the grade after which students use expressions with more than one operator
            if (operator instanceof NumberOperator)
                sandBoxListeners.makeJoinable(newStackPane, commonPane);
            if (operator instanceof UnaryOperator)
                sandBoxListeners.makeJoinable(newStackPane, commonPane);
            if (grade > 3) {
                sandBoxListeners.makeJoinable(newStackPane, commonPane);
            }
            sandBox.getChildren().addAll(newStackPane);
        });
    }

}
