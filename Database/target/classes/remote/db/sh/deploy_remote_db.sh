#!/bin/sh 

# Author			: Cloyd Van S. Secuya
# Filename			: deploy_remote_db.sh
# Date of Creation	: July 26, 2022 
# Description: 
#       A script that can automate the creation of a database schema 
#		deployed on a remote server hosted at Heroku.

# =======================================================
# 				DATABASE CREDENTIALS
# =======================================================
# Platform	: Heroku
# Cloud	: Yes
# Remote Location : Yes
# -------------------------------------------------------
# DATABASE_URL	: postgres://lemzyextdjbmcj:8da3716509fdd2d9e072ec173129a64342f9539bb1e8260f58a07d91bf135c07@ec2-18-214-35-70.compute-1.amazonaws.com:5432/d5oktcpujqqcse
# 
# HOSTNAME	: ec2-18-214-35-70.compute-1.amazonaws.com
# PORT		: 5432
# DATABASE	: d5oktcpujqqcse
# USERNAME	: lemzyextdjbmcj
# PASSWORD : 8da3716509fdd2d9e072ec173129a64342f9539bb1e8260f58a07d91bf135c07
# URI		: postgres://lemzyextdjbmcj:8da3716509fdd2d9e072ec173129a64342f9539bb1e8260f58a07d91bf135c07@ec2-18-214-35-70.compute-1.amazonaws.com:5432/d5oktcpujqqcse
# -------------------------------------------------------

# ------------------------------------------------------------------------------
HOSTNAME="ec2-18-214-35-70.compute-1.amazonaws.com"
PORT=5432
USERNAME="lemzyextdjbmcj"
PASSWORD="8da3716509fdd2d9e072ec173129a64342f9539bb1e8260f58a07d91bf135c07"
SERVER="Heroku"
DATABASE="d5oktcpujqqcse"
URI="postgres://lemzyextdjbmcj:8da3716509fdd2d9e072ec173129a64342f9539bb1e8260f58a07d91bf135c07@ec2-18-214-35-70.compute-1.amazonaws.com:5432/d5oktcpujqqcse"
DATABASE_URL="postgres://lemzyextdjbmcj:8da3716509fdd2d9e072ec173129a64342f9539bb1e8260f58a07d91bf135c07@ec2-18-214-35-70.compute-1.amazonaws.com:5432/d5oktcpujqqcse"
# ------------------------------------------------------------------------------

echo -e "\n\nInitializing Credentials...."
echo "------------------------------------"
echo "HOSTNAME: $HOSTNAME"
echo "PORT: $PORT"
echo "USERNAME: $USERNAME"
echo "SERVER: $SERVER"
echo "DATABASE: $DATABASE"
echo "URI: $URI"
echo "DATABASE_URL: $DATABASE_URL"
echo "------------------------------------"

# Connect to remote server 
echo -e "\n\nConnecting to remote server...$SERVER"
echo -e "Success...Connection at $DATABASE_URL"
psql --host=$HOSTNAME --port=$PORT --dbname=$DATABASE --username=$USERNAME -c "\q"

# Set up the tables and schema
echo -e "\n\nSetting up $DATABASE.Schema"
psql --host=$HOSTNAME --port=$PORT --dbname=$DATABASE --username=$USERNAME -f "../sql/schema.sql" --echo-all

# View and show the databases
echo -e "\n\nViewing available databases on remote at $DATABASE_URL"
psql --host=$HOSTNAME --port=$PORT --dbname=$DATABASE --username=$USERNAME --list
echo "Available databases returned!"

# psql -h $HOSTNAME -p $PORT -U $USERNAME
# psql --host=$HOSTNAME --port=$PORT $DATABASE < "schema.sql"
# psql "$DATABASE_URL < "schema.sql"