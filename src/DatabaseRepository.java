import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseRepository {

    public DatabaseRepository() {
    }
    //Participant participant;


    //public List<Participant> participant = new ArrayList<>();
//create
    public String addItem(Item item) {
        String sql = "INSERT INTO itemtest (iditemtest, name, type, weight, description, effect) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getconnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, item.getId());
            preparedStatement.setString(2, item.getName());
            preparedStatement.setString(3, item.getType());
            preparedStatement.setInt(4, item.getWeight());
            preparedStatement.setString(5, item.getDescription());
            preparedStatement.setInt(6, item.getEffect());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                return ("New item added");
            } else {
                return ("Item not added");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch(Exception e) {
            return "Error inserting item";
        }
        return null;
    }

    //    //read
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM itemtest";
        try (Connection connection = DatabaseConnection.getconnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("iditemtest");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                int weight = resultSet.getInt("weight");
                String description = resultSet.getString("description");
                int effect = resultSet.getInt("effect");

                items.add(new Item(id, name, type, weight, description, effect));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    //update
    public String updateItem(Item item) {
        String sql = "UPDATE itemtest SET name = ?, type = ?, weight = ?, description = ?, effect = ? WHERE iditemtest = ?";

        try (Connection connection = DatabaseConnection.getconnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getType());
            preparedStatement.setInt(3, item.getWeight());
            preparedStatement.setString(4, item.getDescription());
            preparedStatement.setInt(5, item.getEffect());
            preparedStatement.setInt(6, item.getId());

            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("Item updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ("Item updated");

    }

    //    //delete
    public String deleteItem(int id) {
        String sql = "DELETE FROM itemtest WHERE iditemtest = ?";

        try (Connection connection = DatabaseConnection.getconnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int deletedRows = preparedStatement.executeUpdate();
            if (deletedRows > 0) {
                System.out.println("Item number " + id + " deleted");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ("Item deleted");
    }
    public List <Item> initiateInventory(int inventoryId) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT invtest.idinvtest, itemtest.*\n" +
                "FROM invtest\n" +
                "JOIN invhasitemtest ON invtest.idinvtest = invhasitemtest.fkinvtest\n" +
                "JOIN itemtest ON invhasitemtest.fkitemtest = itemtest.iditemtest\n" +
                "WHERE idinvtest = ?";

        try (Connection connection = DatabaseConnection.getconnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, inventoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet != null) {
                while (resultSet.next()) {
                    int iditemtest = resultSet.getInt("iditemtest");
                    String type = resultSet.getString("type");
                    String name = resultSet.getString("name");
                    int weight = resultSet.getInt("weight");
                    String description = resultSet.getString("description");
                    int effect = resultSet.getInt("effect");

                    items.add(new Item(iditemtest, name, type, weight, description, effect));
                }
                    }
                else {
                        return null;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    public String addItemToInventory(int fkinvtest, int fkitemtest) {
        String sql = "INSERT INTO invhasitemtest (fkinvtest, fkitemtest) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getconnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, fkinvtest);
            preparedStatement.setInt(2, fkitemtest);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                return ("Item added to the inventory");
            } else {
                return ("Item not added");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String removeItemFromInventory(int fkinvtest, int fkitemtest) {
        String sql = "DELETE FROM invhasitemtest\nWHERE fkinvtest = ? AND fkitemtest = ?\nLIMIT 1";
        try (Connection connection = DatabaseConnection.getconnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, fkinvtest);
            preparedStatement.setInt(2, fkitemtest);

            int deletedRows = preparedStatement.executeUpdate();
            if (deletedRows > 0) {
                return ("Item removed from the inventory");
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


//        List<Item> items = new ArrayList<>();
//        String sql = "SELECT invtest.idinvtest, itemtest.*\n" +
//                "FROM invtest\n" +
//                "JOIN invhasitemtest ON invtest.idinvtest = invhasitemtest.fkinvtest\n" +
//                "JOIN itemtest ON invhasitemtest.fkitemtest = itemtest.iditemtest";
//        try (Connection connection = DatabaseConnection.getconnection();
//             Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery(sql)) {
//            while (resultSet.next()) {
//                int iditemtest = resultSet.getInt("iditemtest");
//                String type = resultSet.getString("type");
//                String name = resultSet.getString("name");
//                int weight = resultSet.getInt("weight");
//                String description = resultSet.getString("description");
//                int effect = resultSet.getInt("effect");
//
//                items.add(new Item(iditemtest, name, type, weight, description, effect));
//                System.out.println("Her kommer tingene...");
//
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return items;
//    }

//    public Participant getOneParticipant(int userId) {
//        List<Participant> participants = new ArrayList<>();
//        String sql = "SELECT * FROM participants";
//        try (Connection connection = DatabaseConnection.getconnection();
//             Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery(sql)) {
//            while (resultSet.next()) {
//                int id = resultSet.getInt("id");
//                String name = resultSet.getString("name");
//                int max_Drinks = resultSet.getInt("max_Drinks");
//                int current_Drinks = resultSet.getInt("current_Drinks");
//
//                participants.add(new Participant(id, name, max_Drinks, current_Drinks));
//                for (Participant participant : participants) {
//                    if (participant.getId() == userId) {
//                        return participant;
//                    }
//                }
//
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
    public Item getOneItem(int id) {

        String sql = "SELECT * FROM itemtest WHERE iditemtest = ?";
        try (Connection connection = DatabaseConnection.getconnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {

                    int idItemTest = resultSet.getInt("iditemtest");
                    String name = resultSet.getString("name");
                    String type = resultSet.getString("type");
                    int weight = resultSet.getInt("weight");
                    String description = resultSet.getString("description");
                    int effect = resultSet.getInt("effect");
                    if (type.equals("Armor")) {
                        Item item = new Armor(idItemTest, name, type, weight, description, effect);
                        return item;
                    }
                    else if (type.equals("Weapon")){
                        Item item = new Weapon(idItemTest, name, type, weight, description, effect);
                        return item;
                    }
                    else if (type.equals("Consumable")) {
                        Item item = new Consumable(idItemTest, name, type, weight, description, effect);
                        return item;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
//    public List<Participant> getAParticipant() {
//        List<Participant> participants = new ArrayList<>();
//        String sql = "SELECT * FROM participants";
//        try (Connection connection = DatabaseConnection.getconnection();
//             PreparedStatement st = connection.prepareStatement(sql)){
//            try (ResultSet resultSet = st.executeQuery()){
//                while (resultSet.next()){
//                    int id = resultSet.getInt("id");
//                    String name = resultSet.getString("name");
//                    int max_Drinks = resultSet.getInt("max_Drinks");
//                    int current_Drinks = resultSet.getInt("current_Drinks");
//                    participants.add(new Participant(id, name, max_Drinks, current_Drinks));
//
//                }
//            }
//
//        }catch (SQLException e){}
//        return participants;
//    }
//
}
