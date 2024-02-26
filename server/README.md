# How to Run the Server

## Setup a database
Setup a database however you like. The `.db` database file should be in the database folder. I used DB Browser for SQLight to create a database, execute sql to create the tables, and confirm changes made by the server. Use the sql instructions in `database/makeTables.txt` to create the tables for the database.

## Setup your python environment
I used a virtual environment, but you can install the python packages stright up if you want. Create a virtual environment called env by running `python3 -m venv env`. They activate it by running `source env/bin/activate`. Next install the necessary packages by running `pip install -r requirements.txt`.

## Run the Server
Run `flask --app src/todo_server run --host=0.0.0.0 --port=8080`. To test it, run `python test/server_test.py register`. Then run `python test/server_test.py login`.
