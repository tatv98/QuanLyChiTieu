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
        let sql = "SELECT income_id, income_title, income_content, income_amount, user_name, DATE_FORMAT(income_date, '%d/%m/%Y') AS income_date FROM tbl_income ORDER BY income_date DESC"
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
                data.income = response;
                result.data = data;
            }
            console.log(result);
            res.json(result);
        })
    },
    store: (req, res) =>{
        let data = req.body;
        console.log(data);
        let sql = 'INSERT INTO tbl_income (income_title, income_content, income_amount, user_name, income_date) VALUES (?, ?, ?, ?, ?)'
        db.query(sql, [data.income_title, data.income_content, data.income_amount, data.user_name, data.income_date], (err, response) => {
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
        let sql = "SELECT income_id, income_title, income_content, income_amount, user_name, DATE_FORMAT(income_date, '%d/%m/%Y') AS income_date FROM tbl_income WHERE user_name = ? ORDER BY income_date DESC"
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
                data.income = response;
                result.data = data;
            }
            res.json(result);
        })
    },
    update: (req, res) => {
        let data = req.body;
        console.log(data);
        let sql = 'UPDATE tbl_income SET income_title = ? , income_content = ?, income_date = ?, income_amount = ?, user_name = ? WHERE income_id = ?';
        db.query(sql, [data.income_title,data.income_content,data.income_date, data.income_amount, data.user_name, data.income_id], (err, response) => {
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
        let sql = 'DELETE FROM tbl_income WHERE income_id = ?';
        db.query(sql, [req.params.income_id], (err, response) => {
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