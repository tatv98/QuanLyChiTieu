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
        let sql = "SELECT expense_id, expense_title, expense_content, expense_amount, user_name, DATE_FORMAT(expense_date, '%d/%m/%Y') AS expense_date FROM tbl_expense ORDER BY expense_date DESC"
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
                data.expense = response;
                result.data = data;
            }
            console.log(result);
            res.json(result);
        })
    },
    store: (req, res) =>{
        let data = req.body;
        let sql = 'INSERT INTO tbl_expense (expense_title, expense_content, expense_amount, user_name, expense_date) VALUES (?, ?, ?, ?, ?)'
        db.query(sql, [data.expense_title, data.expense_content, data.expense_amount, data.user_name, data.expense_date], (err, response) => {
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
        let sql = "SELECT expense_id, expense_title, expense_content, expense_amount, user_name, DATE_FORMAT(expense_date, '%d/%m/%Y') AS expense_date FROM tbl_expense WHERE user_name = ? ORDER BY expense_date DESC"
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
                data.expense = response;
                result.data = data;
            }
            res.json(result);
        })
    },
    update: (req, res) => {
        let data = req.body;
        //console.log(data.id);
        let sql = 'UPDATE tbl_expense SET expense_title = ? , expense_content = ?, expense_date = ?, expense_amount = ?, user_name = ? WHERE expense_id = ?';
        db.query(sql, [data.expense_title,data.expense_content,data.expense_date, data.expense_amount, data.user_name, data.expense_id], (err, response) => {
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
        let sql = 'DELETE FROM tbl_expense WHERE expense_id = ?';
        console.log(req.params.expense_id);
        db.query(sql, [req.params.expense_id], (err, response) => {
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