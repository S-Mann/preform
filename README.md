# PrefORM
The auto resource/endpoint maker for your REST needs.


This program creates resources from database tables, so that you don't have to write code/queries for everything.

As of now:
⋅⋅⋅Resources are made for [Metamug](https://metamug.com/) and only Postgres Drivers are supported. I'll try to upgrade the script for other frameworks and SQLs as well.
⋅⋅⋅Each table can be passed as a property in te config file. I'll make a way where you can get all the tables in a particular Database and make resources for all.
⋅⋅⋅For the resources made for [Metamug](https://metamug.com/) there are these REST methods available, GET **item/collection**, POST **item**, PUT **item**, DELETE **item**. Where the item is the column **_id_** from the given table.
