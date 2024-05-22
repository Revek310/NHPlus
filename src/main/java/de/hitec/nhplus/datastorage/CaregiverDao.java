package de.hitec.nhplus.datastorage;

import de.hitec.nhplus.model.Caregiver;

import java.sql.*;
import java.util.ArrayList;

/**
 * Implements the Interface <code>DaoImp</code>. Overrides methods to generate specific <code>PreparedStatements</code>,
 * to execute the specific SQL Statements.
 */
public class CaregiverDao extends DaoImp<Caregiver> {


    public CaregiverDao(Connection connection) {
        super(connection);
    }


    /**
     * Generates a caregiver from a resultset
     *
     * @param result Caregiver
     * @return <code>Caregiver</code> Caregiver object
     */
    @Override
    protected Caregiver getInstanceFromResultSet(ResultSet result) throws SQLException {
        return new Caregiver(
                Long.parseLong(result.getString(1)),
                result.getString(2),
                result.getString(3),
                result.getString(4),
                result.getString(5),
                result.getString(6),
                result.getString(7));
    }

    /**
     * Generates an arraylist based on a resultset of caregivers
     *
     * @param result Resultset of caregivers
     * @return <code>Arraylist</code> that contains the caregivers from the resultset
     */
    @Override
    protected ArrayList<Caregiver> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Caregiver> list = new ArrayList<>();
        while (result.next()) {
            Caregiver patient = new Caregiver(result.getInt(1), result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7));
            list.add(patient);
        }
        return list;
    }

    /**
     * Generates a <code>PreparedStatement</code> to create the given caregiver, with the
     * given caregiver object
     * @param caregiver Caregiver object
     * @return <code>PreparedStatement</code> to create the caregiver
     */
    @Override
    protected PreparedStatement getCreateStatement(Caregiver caregiver) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "INSERT INTO caregiver (firstname, surname, telephone, username, password,locked) " +
                    "VALUES (?, ?, ?, ?, ?,?)";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, caregiver.getFirstName());
            preparedStatement.setString(2, caregiver.getSurname());
            preparedStatement.setString(3, caregiver.getTelephone());
            preparedStatement.setString(4, caregiver.getUsername());
            preparedStatement.setString(5, caregiver.getPassword());
            preparedStatement.setString(6, caregiver.isLocked());

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Generates a <code>PreparedStatement</code> to read the given caregiver, identified
     * by the id of the caregiver (cid).
     *
     * @param cid Caregiver id
     * @return <code>PreparedStatement</code> to read the caregiver
     */
    @Override
    protected PreparedStatement getReadByIDStatement(long cid) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "SELECT * FROM caregiver WHERE cid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, cid);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Generates a <code>PreparedStatement</code> to all caregivers
     * @return <code>PreparedStatement</code> to read all caregivers
     */
    @Override
    protected PreparedStatement getReadAllStatement() {
        PreparedStatement statement = null;
        try {
            final String SQL = "SELECT * FROM caregiver";
            System.out.println("SQL: " + SQL);
            statement = this.connection.prepareStatement(SQL);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return statement;
    }

    /**
     * Generates a <code>PreparedStatement</code> to update the given caregiver, identified
     * by the id of the caregiver (cid).
     *
     * @param caregiver Caregiver object
     * @return <code>PreparedStatement</code> to update the caregiver
     */
    @Override
    protected PreparedStatement getUpdateStatement(Caregiver caregiver) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL =
                    "UPDATE caregiver SET " +
                            "firstname = ?, " +
                            "surname = ?, " +
                            "telephone = ?, " +
                            "locked = ? " +
                            "WHERE cid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, caregiver.getFirstName());
            preparedStatement.setString(2, caregiver.getSurname());
            preparedStatement.setString(3, caregiver.getTelephone());
            preparedStatement.setString(4, caregiver.isLocked());
            preparedStatement.setLong(5, caregiver.getCid());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Generates a <code>PreparedStatement</code> to delete the given caregiver, identified
     * by the id of the caregiver (cid).
     *
     * @param cid Caregiver id
     * @return <code>PreparedStatement</code> to delete the caregiver
     */
    @Override
    protected PreparedStatement getDeleteStatement(long cid) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "DELETE FROM caregiver WHERE cid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, cid);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }


}
