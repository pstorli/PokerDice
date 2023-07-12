# PokerDice
PokerDice is a fun, dice based poker game that I thought up and implemented myself.

# Author:   Pete Storli
# Date:     04/24/2023
# Website:  http://storlidesigns.com
# Phone:    971-888-2534 or 971 375 7407
# Company:  Storli Designs LLC
# Address:  4291 Sunset dr, Lake Oswego, OR 97035

Architecture notes:
    I recently compared MVVM and MVI

    Seems that MVI is less flexible due to forcing UDL. (Unidirectional Data Flow)
    This is achieved by having all business logic in one ploace, and keeping the model
    as private as possible.

    So to achieve the same result in MVVM as in MVI, without the pain,
    you just have to be responsible and only have the view consume data
    from the viewModel and have it emit actions back to the viewModel.

2DO Issues:
  0000 Add settings screen back in and allow to change colors.

Resolved Issues Version 1007:
  0000 Updated gradle dependancies.

Resolved Issues Version 1006:
  0000 Fixed issues with alert dialog and snackbar.
  0000 Hopefully last of bugs that need fixing.

Resolved Issues Version 1005:
  0000 Added scaffolding, removed toast and used snackbar instead in SnackBar.kt.
  0000 Added settings screen with scoring instructions.
  0000 Added scoring to hand to beat.
  0000 Added click and dice rolling sound effects.
  0000 Added scoring and cash out. Game basically working.
  0000 Added sound effects.
  0000 Added snack bar.
  0000 Added side drawer for instructions / scoring.
  0000 Added free stuff for level completion.
  0000 Make high score and level get saved.
  0000 Added junit test(s) for the scoring module
  0000 Added Poker Dice title in red at top.
  0000 Added about page.
  0000 Changed icon background to red.
  0000 Fixed hand to beat logic so compare highest die values
  0000 Fixed aboit screen logo.
  0000 Customized the snackbar, cuz the default one is pretty ugly!

Resolved Issues Version 1004:
  0000 Added board
  0000 Added text, textColor and borderColor properties
  0000 Re-did colors, make pink hold, added level
  0000 Realized that color == suit, so changed to do just that.

Resolved Issues Version 1003:
  0000 Created initial Jetpack Compose from poker dice screen shot
  0000 Added game state to pokerviewmodel
  0000 split composeables into separate files and screens
  0000 Added hand to beat
  0000 made hand to beat and player row same height.
  0000 Added color to dice.

Resolved Issues Version 1002:
    0000 Added Jetpack Compose test button

Resolved Issues Version 1001:
  0000 Created initial MVVM architecture.

Resolved Issues Version 1000:
  0000 Init Commit
  0000 Added transparent dice images
  0000 Updatd 1-6 dice, they were shifted too far up and left.
  0000 Added square and round corner empty background squares


