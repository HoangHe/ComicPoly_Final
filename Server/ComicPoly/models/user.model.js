var db = require('./db');
const jwt = require('jsonwebtoken')  ;//  Cần chạy lệnh cài đặt: npm install jsonwebtoken --save
require('dotenv').config(); // su dung thu vien doc file env:   npm install dotenv --save
const chuoi_ky_tu_bi_mat = process.env.TOKEN_SEC_KEY;
const bcrypt = require("bcrypt"); //cài bằng lệnh:  npm install bcrypt  --save 


const userSchema = new db.mongoose.Schema(
    {
        userName: { type: String, require: true },
        passWord: { type: String, require: true },
        email: { type: String, require: true },
        fullName: { type: String, require: true },
        token: {  // trường hợp lưu nhiều token thì phải dùng mảng. Trong demo này sẽ dùng 1 token
            type: String,
            required: false
        }
    },
    {
        collection: 'user'
    }
);

userSchema.methods.generateAuthToken = async function () {

    const user = this
    console.log(user)
    const token = jwt.sign({_id: user._id, userName: user.userName}, chuoi_ky_tu_bi_mat)
    // user.tokens = user.tokens.concat({token}) // code này dành cho nhiều token, ở demo này dùng 1 token
    user.token = token;
    await user.save()
    return token
 }
 
 // dùng cho đăng nhập: 
 userSchema.statics.findByCredentials = async (userName, passWord) => {

    const user = await userModel.findOne({userName})
    if (!user) {
        throw new Error('Tài khoản không tồn tại')
    }
    const isPasswordMatch = await bcrypt.compare(passWord, user.passWord)
    if (!isPasswordMatch) {
        throw new Error('Sai mật khẩu')
    }
    return user
}
 

let userModel = db.mongoose.model('userModel', userSchema);

module.exports = {userModel};