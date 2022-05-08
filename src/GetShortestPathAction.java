import javax.swing.*;
import java.awt.event.ActionEvent;

public class GetShortestPathAction extends AbstractAction {

    private ActionChoice actionChoice;

    public GetShortestPathAction(ActionChoice actionChoice) {
        super("Shortest Path");
        this.actionChoice = actionChoice;
    }

    public void actionPerformed(ActionEvent e) {
        actionChoice.showIndication(1);

    }
}
