'use strict'

const util = require('util')
const mysql = require('mysql')
const db = require('./../db')

module.exports = {
    index: (req, res) => {
        let result = new Object;
        result.status = "success";
        result.message = "API active!!!";
        result.data = null;
        res.json(result);
    },
    get: (req, res) => {
        let sql = 'SELECT * FROM tbl_user'
        db.query(sql, (err, response) => {
            let result = new Object;
            let data = new Object;
            if (err) {
                result.status = "failed";
                result.message = "Can't retrieve user!";
                data.error = err;
                result.data = data;
            }else{
                result.status = "success";
                result.message = "OK";
                data.user = response;
                result.data = data;
            }
            console.log(result);
            res.json(result);
        })
    },
    store: (req, res) =>{
        let data = req.body;
        let sql = 'INSERT INTO tbl_user SET ?'
        db.query(sql, [data], (err, response) => {
            let result = new Object;
            let data = new Object;
            if (err) {
                result.status = "failed";
                result.message = "Save failed!";
                data.error = err;
                result.data = data;
            }else{
                result.status = "success";
                result.message = "Save success!";
                result.data = null;
            }
            res.json(result);
        });
    },
    detail: (req, res) => {
        let sql = 'SELECT * FROM tbl_user WHERE user_name = ?'
        db.query(sql, [req.params.user_name], (err, response) => {
            let result = new Object;
            let data = new Object;
            if (err) {
                result.status = "failed";
                result.message = "Get detail failed";
                data.error = err;
                result.data = data;
            }else{
                result.status = "success";
                result.message = "success";
                data.user = response;
                result.data = data;
            }
            res.json(result);
        })
    },
    update: (req, res) => {
        let data = req.body;
        //console.log(data.id);
        let sql = 'UPDATE tbl_user SET user_name = ? , user_pass = ?, user_age = ?, user_gender = ? WHERE user_id = ?';
        db.query(sql, [data.user_name,data.user_pass,data.user_age, data.user_gender, data.user_id], (err, response) => {
            let result = new Object;
            let data = new Object;
            if (err) {
                result.status = "failed";
                result.message = "Update failed";
                data.error = err;
                result.data = data;
            }else{
                result.status = "success";
                result.message = "Update success";
                result.data = null;
            }
            res.json(result);
        });
    },
    delete: (req, res) => {
        let data = req.params;
        console.log(req);
        let sql = 'DELETE FROM tbl_user WHERE id = ?';
        db.query(sql, [data.id], (err, response) => {
            let result = new Object;
            let data = new Object;
            if (err) {
                result.status = "failed";
                result.message = "Delete failed";
                data.error = err;
                result.data = data;
            }else{
                result.status = "success";
                result.message = "Delete success";
                result.data = null;
            }
            res.json(result);
        });
    },
}