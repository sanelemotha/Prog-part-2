package progassignment;
import javax.swing.JOptionPane;


public class chatmenu {

    public void quickchatMenu() {
        int userChoice;

        do {
            JOptionPane.showMessageDialog(null, 
                "===Welcome to the Main Menu===\nChoose one of the following features: ", 
                "Main Menu", JOptionPane.PLAIN_MESSAGE);

            String input = JOptionPane.showInputDialog(null, 
                "1. Send Messages\n2. Show recently sent messages\n3. Quit", 
                "OPTIONS", JOptionPane.PLAIN_MESSAGE);

            userChoice = Integer.parseInt(input);

            switch (userChoice) {
                case 1:
                    sendingmessages sm = new sendingmessages();
                    sm.sendMessage();
                    break;

                case 2:
                    recentmessages rm = new recentmessages();
                    rm.displayMenu();
                    break;

                case 3:
                    JOptionPane.showMessageDialog(null, "Program Stopped");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice.");
                    break;
            }

        } while (userChoice != 3);
    }
}
