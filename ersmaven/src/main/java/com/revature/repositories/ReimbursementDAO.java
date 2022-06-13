package com.revature.repositories;

import com.revature.models.Reimbursement;
import com.revature.models.Role;
import com.revature.models.Status;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ReimbursementDAO {

	/**
	 * Should retrieve a Reimbursement from the DB with the corresponding id or an
	 * empty optional if there is no match.
	 */
	public Optional<Reimbursement> getById(int id) {
		Reimbursement reimburse = new Reimbursement();
		try {
			Connection conn = ConnectionFactory.getInstance().getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from reimbursement where id=" + id);
			if (rs.next()) {

				reimburse.setId(rs.getInt("id"));
				reimburse.setAmount(rs.getDouble("amount"));
				reimburse.setAuthor(rs.getInt("author"));
				reimburse.setResolver(rs.getInt("resolver"));
				if (rs.getString("status").equalsIgnoreCase("pending")) {
					reimburse.setStatus(Status.PENDING);
				} else if (rs.getString("status").equalsIgnoreCase("denied")) {
					reimburse.setStatus(Status.DENIED);
				} else if (rs.getString("status").equalsIgnoreCase("approved")) {
					reimburse.setStatus(Status.APPROVED);
				}
				reimburse.setCreationDate(rs.getDate("creation_date"));
				reimburse.setResolutionDate(rs.getDate("resolution_date"));
				reimburse.setReceipt(rs.getObject("receipt_image"));

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return Optional.of(reimburse);
	}

	/**
	 * Should retrieve a List of Reimbursements from the DB with the corresponding
	 * Status or an empty List if there are no matches.
	 */
	public List<Reimbursement> getByStatus(Status status) {
		List<Reimbursement> reimbursementList = new ArrayList<Reimbursement>();

		try {
			Connection conn = ConnectionFactory.getInstance().getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from reimbursement where status=" + status);
			while (rs.next()) {
				Reimbursement reimburse = new Reimbursement();
				reimburse.setId(rs.getInt("id"));
				reimburse.setAmount(rs.getDouble("amount"));
				reimburse.setAuthor(rs.getInt("author"));
				reimburse.setResolver(rs.getInt("resolver"));
				if (rs.getString("status").equalsIgnoreCase("pending")) {
					reimburse.setStatus(Status.PENDING);
				} else if (rs.getString("status").equalsIgnoreCase("denied")) {
					reimburse.setStatus(Status.DENIED);
				} else if (rs.getString("status").equalsIgnoreCase("approved")) {
					reimburse.setStatus(Status.APPROVED);
				}
				reimburse.setCreationDate(rs.getDate("creation_date"));
				reimburse.setResolutionDate(rs.getDate("resolution_date"));
				reimburse.setReceipt(rs.getObject("receipt_image"));
				reimbursementList.add(reimburse);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return reimbursementList;
	}

	/**
	 * <ul>
	 * <li>Should Update an existing Reimbursement record in the DB with the
	 * provided information.</li>
	 * <li>Should throw an exception if the update is unsuccessful.</li>
	 * <li>Should return a Reimbursement object with updated information.</li>
	 * </ul>
	 */
	public Reimbursement update(Reimbursement unprocessedReimbursement) {
		Reimbursement reimburse = new Reimbursement();
		try {
			Connection conn = ConnectionFactory.getInstance().getConnection();
			String updateQuery = "UPDATE reimbursement SET resolver = ?, status = ?,resolution_date = CURRENT_TIMESTAMP WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(updateQuery);
			pstmt.setInt(1, unprocessedReimbursement.getResolver());
			if (unprocessedReimbursement.getStatus().equals("APPROVED"))
				pstmt.setObject(2, Status.APPROVED);
			if (unprocessedReimbursement.getStatus().equals("DENIED"))
				pstmt.setObject(2, Status.DENIED);
			pstmt.setInt(3, unprocessedReimbursement.getId());
			pstmt.execute();

			ResultSet rs = conn.createStatement()
					.executeQuery("select * from reimbursement where id=" + unprocessedReimbursement.getId());
			if (rs.next()) {
				reimburse.setId(rs.getInt("id"));
				reimburse.setAmount(rs.getDouble("amount"));
				reimburse.setAuthor(rs.getInt("author"));
				reimburse.setResolver(rs.getInt("resolver"));

				reimburse.setDescription(rs.getString("description"));
				reimburse.setCreationDate(rs.getDate("creation_date"));
				reimburse.setResolutionDate(rs.getDate("resolution_date"));
				if (rs.getString("status").equalsIgnoreCase("pending"))
					reimburse.setStatus(Status.PENDING);
				if (rs.getString("status").equalsIgnoreCase("Approved"))
					reimburse.setStatus(Status.APPROVED);
				if (rs.getString("status").equalsIgnoreCase("Denied"))
					reimburse.setStatus(Status.DENIED);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return reimburse;
	}

	public List<Reimbursement> getReimbursementByAuthor(int id) {
		List<Reimbursement> reimbursementList = new ArrayList<Reimbursement>();

		try {
			Connection conn = ConnectionFactory.getInstance().getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from reimbursement where author=" + id);
			while (rs.next()) {
				Reimbursement reimburse = new Reimbursement();
				reimburse.setId(rs.getInt("id"));
				reimburse.setAmount(rs.getDouble("amount"));
				reimburse.setAuthor(rs.getInt("author"));
				reimburse.setResolver(rs.getInt("resolver"));
				if (rs.getString("status").equalsIgnoreCase("pending")) {
					reimburse.setStatus(Status.PENDING);
				} else if (rs.getString("status").equalsIgnoreCase("denied")) {
					reimburse.setStatus(Status.DENIED);
				} else if (rs.getString("status").equalsIgnoreCase("approved")) {
					reimburse.setStatus(Status.APPROVED);
				}
				reimburse.setCreationDate(rs.getDate("creation_date"));
				reimburse.setResolutionDate(rs.getDate("resolution_date"));
				reimburse.setReceipt(rs.getObject("receipt_image"));
				reimbursementList.add(reimburse);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return reimbursementList;
	}
}
