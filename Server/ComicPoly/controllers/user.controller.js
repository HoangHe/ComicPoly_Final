var myMD = require("../models/user.model");


exports.list = async (req, res, next)=>{
    try {
        const user =  await myMD.userModel.find();
        console.log(user, "LIST USER");
        return res.status(200).json({data: user});
    } catch (error) {
        console.log(error);
        res.status(500).send(error.message)
    }
}


exports.add = async (req, res, next)=>{
    try {
        //tạo một chuỗi salt dùng để mã hóa mật khẩu
        const salt = await bcrypt.genSalt(10);
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
        return res.status(500).json({msg: error.message})
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
