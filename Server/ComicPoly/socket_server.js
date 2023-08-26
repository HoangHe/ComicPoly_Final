const io = require("socket.io")();
const socketapi = {
  io: io,
};
//==== Viết code tương tác ở trước dòng export

io.on("connection", (client) => {
  console.log("Client connected : " + client.id);
  // định nghĩa 1 sự kiện
  client.on("New Comic", (data) => {
    // nhận dữ liệu từ client gửi lên
    console.log("New msg: " + data);
    // gửi phản hồi
    client.emit("New Comic", "Truyện mới " + data);
  });

  client.on("New User", (data) => {
    // nhận dữ liệu từ client gửi lên
    console.log("Người dùng vừa đăng kí: "+ data);
    // gửi phản hồi
    client.emit("New User", "Người dùng vừa đăng kí: " + data);
  });

  // sự kiện ngắt kết nối

  client.on("disconnect", () => {
    console.log("Client disconected!");
  });
});

module.exports = socketapi;
