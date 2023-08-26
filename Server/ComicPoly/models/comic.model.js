var db = require('./db');

const comicSchema = new db.mongoose.Schema(
    {
        name: { type: String, require: true },
        desc: { type: String, require: true },
        author: { type: String, require: true },
        publish: { type: String, require: true },
        image: { type: String, require: true },
        content: { type: Array, require: true },
    },
    {
        collection: 'comic'
    }
);
let comicModel = db.mongoose.model('comicModel', comicSchema);

module.exports = {comicModel};