var myMD = require("../models/comic.model");
var socket = require("../socket_server");


exports.list = async (req, res, next) =>{
    try {
        const comics = await myMD.comicModel.find();
        return res.status(200).json(comics);
    } catch (err) {
        return res.status(500).json({ error: err.message });
    }
}

exports.add = async (req, res, next) =>{
    if (req.method == 'POST') {
        try {
            if(req.body.name && req.body.desc && req.body.author && req.body.publish && req.body.image && req.body.content){
                const comic = new myMD.comicModel(req.body);
                await comic.save();
                socket.io.emit("New Comic", comic.name);
                console.log("New Comic :" ,comic.name);
                return res.json(comic);
            }else{
                return res.status(404).json({ success: false, message: "Vui lòng nhập đầy đủ thông tin" });
            }
            
        } catch (err) {
            return res.status(500).json({ error: err.message });
        }
    }
}

exports.addContentToComic = async (req, res, next) =>{
    if (req.method == 'PUT') {
        try {
            const comic = await myMD.comicModel.findById(req.body.id);
            if (!comic) {
              return res.status(404).json({ success: false, message: "Comic not found" });
            }
        
            comic.content.push(req.body.content);
            await comic.save();
        
            return res.status(200).json({comic, success: true, message: "Image added to comic" });
          } catch (error) {
            console.error(error);
            return res.status(500).json({ success: false, message: "Error adding image to comic" });
          }
    }
}

exports.deleteContent = async (req, res, next) =>{
    if (req.method == 'PUT') {
        try {
            const comic = await myMD.comicModel.findById(req.body.id);
            if (!comic) {
              return res.status(404).json({ success: false, message: "Comic not found" });
            }
        
            comic.content.pull(req.body.content);
            await comic.save();
        
            return res.status(200).json({comic, success: true, message: "Image deleted to comic" });
          } catch (error) {
            console.error(error);
            return res.status(500).json({ success: false, message: "Error delete image to comic" });
          }
    }
}

exports.update = async (req, res, next) =>{

    if (req.method == 'PUT') {
        try {
            const comic = await myMD.comicModel.findByIdAndUpdate(req.body._id, req.body, { new: true });
            if (!comic) {
                return res.status(404).json({ error: 'Comic not found' });
            }
            return res.json(comic);
        } catch (err) {
            return res.status(500).json({ error: err.message });
        }
    }
}

exports.delete = async (req, res, next) =>{
    console.log(req.query.id);
    try {
        const comic = await myMD.comicModel.findByIdAndDelete(req.query.id);
        if (!comic) {
            return res.status(404).json({ error: 'Comic not found' });
        }
        return res.json({ message: 'Comic deleted successfully' });
    } catch (err) {
        return res.status(500).json({ error: err.message });
    }
}
