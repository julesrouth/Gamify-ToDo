# How to Run the Server

## Setup a database


## Setup your python environment
I used a virtual environment, but you can install the python packages stright up if you want. Create a virtual environment called env by running `python3 -m venv env`. They activate it by running `source env/bin/activate`. Next install the necessary packages by running `pip install -r requirements.txt`.

## Run the Server
Run `flask --app src/todo_server run --host=0.0.0.0`. To test it, run `python test/server_test.py register`. Then run `python test/server_test.py login`.
