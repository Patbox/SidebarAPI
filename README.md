# Sidebar API
It's a small, jij-able library for creation of server side sidebars (scoreboards)
with full support of Minecraft's text components 
(which allow custom fonts/colors and unlimited line length).

## Usage:
Add it to your dependencies like this:

```
repositories {
	maven { url 'https://maven.nucleoid.xyz' }
}

dependencies {
	modImplementation include("eu.pb4:sidebar-api:[TAG]")
}
```

After that creating a new sidebar is as easy as creating new instance of 
Sidebar class, setting its content and adding players to it.

### Proposed priorities:
- LOWEST - Fallback sidebars
- LOW - General sidebars visible most of the time (with basic information like player's currency)
- NORMAL - Sidebars visible only in selected areas/worlds/etc
- HIGH - Sidebars that are visible after certain actions for only seconds (level ups, voting, etc)
- OVERRIDE - Only for development