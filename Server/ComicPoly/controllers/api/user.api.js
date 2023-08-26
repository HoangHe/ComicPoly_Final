var myMD = require('../../models/user.model');
const bcrypt = require("bcrypt");  


exports.login = async (req, res, next)=>{
    try {
        const user = await myMD.userModel
                    .findByCredentials(req.body.userName, req.body.passWord)
        if (!user) {
            return res.status(401)
                    .json({msg: 'Sai thông tin đăng nhập'})
        }
        // đăng nhập thành công, tạo token làm việc mới
        const token = await user.generateAuthToken()

        console.log(user, "Đăng Nhập");
        return res.status(200).json({ user, token , msg: "Đăng nhập thành công"})

    } catch (error) {
        console.log(error)
        return res.status(500).json({msg: error.message})    
    }  
}

exports.register = async (req, res, next)=>{
    try {
        //tạo một chuỗi salt dùng để mã hóa mật khẩu
        const salt = await bcrypt.genSalt(10);
        const check_user = await myMD.userModel.findOne({userName : req.body.userName});
        if(check_user != null){
            return res.status(204).json({ msg: "Tài khoản đã tồn tại."})
        }

        console.log(salt, req.body);
        if(req.body.userName && req.body.passWord && req.body.email && req.body.fullName){
            const user = new myMD.userModel(req.body);

            user.passWord = await bcrypt.hash(req.body.passWord, salt);

            const token = await user.generateAuthToken();

            let new_u = await user.save();
            console.log(new_u,"ĐĂNG KÍ");
            return res.status(201).json({ user: new_u , token, msg : "Đăng kí thành công"})
        }else{
            return res.status(404).json({ msg : "Vui lòng nhập đầy đủ thông tin"})
        }

    } catch (error) {
        console.log(error)
        return res.status(500).json({ msg: error.message})
    }
}

exports.logout = async (req, res, next) => {
    try {
      const userId = req.query.id;
  
      // Tìm người dùng dựa trên userId
      const user = await myMD.userModel.findById(userId);
  
      if (!user) {
        return res.status(404).json({ msg: 'Người dùng không tồn tại' });
      }
  
      // Xóa token của người dùng
      user.token = null;
      await user.save();
  
      return res.status(200).json({ msg: 'Đăng xuất thành công' });
    } catch (error) {
      console.log(error);
      res.status(500).send(error.message);
    }
  };

exports.list = async (req, res, next)=>{
    try {
        const user =  await myMD.userModel.find();
        console.log(user, "LIST USER");
        return res.status(200).json(user);
    } catch (error) {
        console.log(error);
        res.status(500).send(error.message)
    }
}


exports.update = async (req, res, next)=>{
    try {
        const id = req.body.id;
        const newData = req.body;
        const updatedData = await myMD.userModel.findOneAndUpdate(
                { _id: id },
                { $set: newData },
                { returnOriginal: false }
            );
            if (!updatedData) {
                return res.status(404).json({ error: 'Dữ liệu không tìm thấy' });
            }
            return res.json({ success: true, msg: 'Cập nhật thành công' });
        } catch (error) {
            console.log(error);
            return res.status(500).json({ error: 'Lỗi khi cập nhật dữ liệu' });
        }
}

exports.delete = async (req, res, next)=>{
    console.log(req.body.id,"ID DELETE");
    try {
        const id = req.body.id;
        const deletedData = await myMD.userModel.findOneAndDelete({ _id: id });

        if (deletedData.deletedCount === 0) {
            return res.status(404).json({ error: 'Dữ liệu không tìm thấy' });
        }
        return res.json({ success: true, message: 'Xóa dữ liệu thành công' });

    } catch (error) {
        console.log(error);
        res.status(500).send(error.message)
    }
}


