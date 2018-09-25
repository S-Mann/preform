# PrefORM
The auto resource/endpoint maker for your REST needs.

Want to migrate to a framework but have tonnes of tables and you can't deal with the resource migration from one framework to another or setup a new back-end using a new framework altogether? Well this script will ease your process if not automate it completely.

### Steps:

1. Fill in the configurations in the settings.properties file.
2. Run the script.(Do not tamper any other file(s) in config folder other than settings.properties unless you know what you are doing)
3. Check the generated folder for the generated resources.

This script creates resources from database tables, so that you don't have to write code/queries for everything.

#### Footnotes:
1. Resources are made for [Metamug](https://metamug.com/) and only Postgres Drivers are supported. I'll try to upgrade the script for other frameworks and SQLs as well.
2. Each table can be passed as a property in te config file. I'll make a way where you can get all the tables in a particular Database and make resources for all.
3. For the resources made for [Metamug](https://metamug.com/) there are these REST methods available, GET **item/collection**, POST **item**, PUT **item**, DELETE **item**. Where the item is the column **_id_** from the given table.
