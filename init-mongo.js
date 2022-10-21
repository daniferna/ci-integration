db.createUser(
    {
        user: "expensesAdmin",
        pwd: "expensesAdmin2022",
        roles: [{ role: "readWrite", db: "shared-expenses" }]
    }
);