'use strict';
module.exports = function (app) {
    let user = require('./controllers/user_controller.js');
    let expense = require('./controllers/expense_controller.js');
    let income = require('./controllers/income_controller.js');

    app.route('/')
        .get(user.index);

    app.route('/user')
        .get(user.get)
        .post(user.store);

    app.route('/user/:user_name')
        .get(user.detail)
        .delete(user.delete);

    app.route('/user/update')
        .post(user.update);


    app.route('/expense')
        .get(expense.get);
    app.route('/expense/add')
        .post(expense.store);

    app.route('/expense/:user_name')
        .get(expense.detail);
    app.route('/expense/:expense_id')
        .delete(expense.delete);

    app.route('/expense/update')
        .post(expense.update);



    app.route('/income')
        .get(income.get);
    app.route('/income/add')
        .post(income.store);
    app.route('/income/:user_name')
        .get(income.detail);
    app.route('/income/:income_id')
        .delete(income.delete);

    app.route('/income/update')
        .post(income.update);


};