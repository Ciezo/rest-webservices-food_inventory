#!/bin/sh 

# Author			: Cloyd Van S. Secuya
# Filename			: drop_tbl.sh
# Date of Creation	: July 26, 2022 
# Description: 
#       If in any case it requires to drop all the two tables from the database. 
#		Then, we need to run this script

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

# Begin dropping tables
psql --host=$HOSTNAME --port=$PORT --dbname=$DATABASE --username=$USERNAME < "drops.sql"