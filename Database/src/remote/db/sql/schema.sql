/*
 * Author			: Cloyd Van S. Secuya
 * Date of Creation	: July 26, 2022 
 * Description		: 
 * 		This SQL Schema file is responsible for building up the remote database hosted on Heroku
 */

/* =======================================================
 * 				DATABASE CREDENTIALS
 * =======================================================
 * Platform	: Heroku
 * Cloud	: Yes
 * Remote Location : Yes
 * -------------------------------------------------------
 * DATABASE_URL	: postgres://lemzyextdjbmcj:8da3716509fdd2d9e072ec173129a64342f9539bb1e8260f58a07d91bf135c07@ec2-18-214-35-70.compute-1.amazonaws.com:5432/d5oktcpujqqcse
 * 
 * HOSTNAME	: ec2-18-214-35-70.compute-1.amazonaws.com
 * PORT		: 5432
 * DATABASE	: d5oktcpujqqcse
 * USERNAME	: lemzyextdjbmcj
 * PASSWORD : 8da3716509fdd2d9e072ec173129a64342f9539bb1e8260f58a07d91bf135c07
 * URI		: postgres://lemzyextdjbmcj:8da3716509fdd2d9e072ec173129a64342f9539bb1e8260f58a07d91bf135c07@ec2-18-214-35-70.compute-1.amazonaws.com:5432/d5oktcpujqqcse
 * -------------------------------------------------------
 */

BEGIN;

	-- Tables
	CREATE TABLE IF NOT EXISTS Product_tbl
	(
		Product_code	VARCHAR(50)	NOT NULL,
	    Product_name	VARCHAR(50)	NOT NULL,
	    Price 			DOUBLE PRECISION	NOT NULL,
	    
	    PRIMARY KEY (Product_code)
	);
	
	CREATE TABLE IF NOT EXISTS Cart_tbl
	(
		Cart_ID					VARCHAR(50)	NOT NULL,
		Product_code_at_cart	VARCHAR(50) REFERENCES Product_tbl (Product_code),
	    Product_name			VARCHAR(50)	NOT NULL,
	    Price 					DOUBLE PRECISION	NOT NULL
	);
	
	CREATE TABLE IF NOT EXISTS User_tbl
	(
		User_ID		VARCHAR(50) NOT NULL,
	    User_name	VARCHAR(50) NOT NULL,
	    Email		VARCHAR(50) NOT NULL,
	    User_pswd   VARCHAR(50)	NOT NULL,
	    First_name	VARCHAR(50) NOT NULL,
	    Last_name	VARCHAR(50) NOT NULL,
	    
	    PRIMARY KEY (User_ID)
	);

END;