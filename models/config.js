const mongoose = require("mongoose");

const guildSchema = mongoose.Schema({
  _id: mongoose.Schema.Types.ObjectId,
  guildID: String,
  eventMessageProbability: String,
  eventVoiceProbability: String,
});

module.exports = mongoose.model("Config", guildSchema, "config");
