# Sesens
It's a plugin that adds the possibility to find scrolls with special powers.

## Cycle
To start the cycle, type in the chat '/sesens startcycle'. \
This does require sesens.commands.admin permission; This defaults to OP. \
After this treasures will be scattered in a 600x600 field. \
In the tomb (that spawns between 700x700) you can find a summoner's stone and \
with that you can re-summon treasures and the tomb.

## Commands
 - `/sesens storage`: Opens up your scroll storage (without `sesens.command.admin`).
 - `/sesens storage [player]`: Opens up that player's storage (with `sesens.command.admin`).
 - `/sesens lvl`: Displays your level.
 - `/sesens lvl [player]`: Displays your level (requires `sesens.command.admin`).
 - `/sesens setlvl [player] [lvl]`: Sets that player's level (requires `sesens.command.admin`).
 - `/sesens give [item]`: Gives that item to the executing player (requires `sesens.command.admin`).
 - `/sesens startcycle`: Starts the cycle (requires `sesens.command.admin`).
 - `/sesens help`: Gives instructions of the item in the main hand.
 - `/sesens help [item]`: Gives instructions of that item.

## Permissions
 - `sesens.command.use`:
Permission for using the commands.
 - `sesens.command.admin`:
Permission for using the admin commands.
 - `sesens.setStorage`:
Permission for setting a scroll storage.
 - `sesens.canUseScroll`:
Permission for using a scroll.