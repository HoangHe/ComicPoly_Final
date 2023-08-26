var express = require('express');
var router = express.Router();
var userController = require("../controllers/user.controller")

router.post('/add', userController.add); 
router.get('/list', userController.list);
router.put('/update', userController.update);
router.delete('/delete', userController.delete);

module.exports = router;
