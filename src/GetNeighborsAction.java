import javax.swing.*;
import java.awt.event.ActionEvent;

public class GetNeighborsAction extends AbstractAction {

    private ActionChoice actionChoice;

    public GetNeighborsAction(ActionChoice actionChoice) {
        super("Get Neighbors");
        this.actionChoice = actionChoice;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        actionChoice.showIndication(0);
    }
}