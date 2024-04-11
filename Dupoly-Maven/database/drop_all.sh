#!/bin/bash

database=duopoly

# # drop all current tables
psql -h localhost -U postgres -d $database -f database/drop_all.sql
