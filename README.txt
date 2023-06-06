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
  0000

Resolved Issues Version 1003:
  0000 Created initial Jetpack Compose from poker dice screen shot
  0000 Added game state to pokerviewmodel
  0000 split composeables into separate files and screens

Resolved Issues Version 1002:
    0000 Added Jetpack Compose test button

Resolved Issues Version 1001:
  0000 Created initial MVVM architecture.

Resolved Issues Version 1000:
  0000 Init Commit
  0000 Added transparent dice images
  0000 Updatd 1-6 dice, they were shifted too far up and left.
  0000 Added square and round corner empty background squares


