const mongoose = require("mongoose");
const Guild = require("../models/guild");
const Config = require("../models/config");
var clc = require("cli-color");

module.exports = async (client, guild) => {
  console.log(clc.yellow("[-] Removed from server"));
  Guild.findOneAndDelete(
    {
      guildID: guild.id,
    },
    (err, res) => {
      if (err) console.error(clc.red(err));
    }
  );
  Config.findOneAndDelete(
    {
      guildID: guild.id,
    },
    (err, res) => {
      if (err) console.error(clc.red(err));
    }
  );
};
