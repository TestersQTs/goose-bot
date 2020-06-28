const mongoose = require("mongoose");
const Guild = require("../models/guild");
const Conf = require("../models/config");
const { eventNames } = require("../models/guild");
var clc = require("cli-color");

module.exports = async (client, guild) => {
  var gID = guild.id;
  guild = new Guild({
    _id: mongoose.Types.ObjectId(),
    guildID: gID,
    guildName: guild.name,
    guildPrefix: process.env.PREFIX,
  });

  await guild.save().catch((err) => console.error(clc.red(err)));

  conf = new Conf({
    _id: mongoose.Types.ObjectId(),
    guildID: gID,
    eventMessageProbability: "1",
    eventVoiceProbability: "1",
  });

  await conf.save().catch((err) => console.error(clc.red(err)));

  console.log(clc.green("[+] Joined new server"));
};
