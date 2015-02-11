Gender = {
	Male:			'Male',
	Female:			'Female',
	Unknown:		'Unknown'
};

UserStatus = {
	Newbie:			'Newbie',
	Family:			'Family',
	Stammi:			'Stammi',
	HonoryMember:	'HonoryMember',
	Admin:			'Admin',
	Sysadmin:		'Sysadmin',
	SystemBot:		'SystemBot'
};

UserType = {
	Human:			'Human',
	AppBot:			'AppBot',
	SystemBot:		'SystemBot'
};

KnuddelsServer				= com.knuddels.apps.core.KnuddelsServer;
DiceConfigurationFactory	= com.knuddels.apps.gamesupport.dice.DiceConfigurationFactory;
ChannelJoinPermission		= com.knuddels.apps.channel.ChannelJoinPermission;
KnuddelAmount				= com.knuddels.apps.tools.KnuddelAmount;
StringOperations			= com.knuddels.apps.tools.StringOperations;
RandomOperations			= com.knuddels.apps.tools.RandomOperations;

function require(fileName) {
	KnuddelsServer.require(fileName);
}

clearInterval = (function(id) {
	KnuddelsServer.clearInterval(id);
});

setInterval = (function(callback, milliseconds) {
	return KnuddelsServer.setInterval(callback, milliseconds);
});

clearTimeout = (function(id) {
	KnuddelsServer.clearTimeout(id);
});

setTimeout = (function(callback, milliseconds) {
	return KnuddelsServer.setTimeout(callback, milliseconds);
});