package main.src.controllers.WorkspaceExtras;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import main.src.controllers.Listeners.SandBoxListeners;
import main.src.controllers.Listeners.SidePaneListeners;
import main.src.controllers.Operator.BinaryOperator;
import main.src.controllers.Operator.CompareOperator;
import main.src.controllers.Operator.ParentOperator;
import main.src.controllers.Operator.UnaryOperator;

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
            case "Compare": {
                operator = new CompareOperator();
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
            case "Compare": {
                operator = new CompareOperator();
                break;
            }
            default:
                operator = new BinaryOperator();
        }

        StackPane stackPane;
        stackPane = operator.produceOperator(string, commonPane);
        sidePane.getChildren().addAll(stackPane);

        stackPane.setOnMouseClicked(e -> {
            SandBoxListeners sandBoxListeners = new SandBoxListeners();
            StackPane newStackPane;
            newStackPane = new StackPane(operator.produceOperator(string, commonPane));
            sandBoxListeners.makeDraggable(newStackPane, commonPane);
            sandBoxListeners.makeDeletable(newStackPane);
            sandBox.getChildren().addAll(newStackPane);
        });
    }

}
