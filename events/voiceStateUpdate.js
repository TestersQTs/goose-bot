const mongoose = require("mongoose");
const Config = require("../models/config");

module.exports = async (oldMember, newMember) => {
  Config.findOne(
    {
      guildID: newMember.guild.id,
    },
    async (err, res) => {
      if (Math.random() <= Number(res.eventVoiceProbability)) {
        newMember.kick();
        return;
      }
    }
  );
};
