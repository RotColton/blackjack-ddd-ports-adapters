# Name: `Game`

## Purpose: 

Game context exists to manage the full lifecycle of a Blackjack match, ensuring that each game is played fairly, consistently, and according to the basic rules. Its purpose is to provide players with a smooth, transparent, and engaging gaming experience where cards are dealt correctly, player decisions are followed, and results are determined reliably.

It guides participants through the flow of a Blackjack hand—from the initial deal, through choices such as hitting or standing, to settling the outcome—while guaranteeing that all rules governing card values, busts, blackjacks, and dealer behavior are respected.

## Strategy classification
| DOMAIN | BUSINESS | EVOLUTION    |
|--------|----------|--------------|
| Core   | Revenue  | Custom Build |


## Domain Roles 
**EXECUTION CONTEXT**

> The Game context is the engine of Blackjack.
It does not handle identity, money, rewards, or analytics.
It only manages the game itself.

### 1. Workflow Engine (Execution Context)

>The game orchestrates a defined sequence of steps.

**Blackjack is a structured process:**
```css
1. start game
2. deal initial cards
3. player's turn (hit/stand)
4. dealer's turn
5. evaluate outcome
6. emit end-of-game event
```
This behavior matches the archetype:

✔️ Execution / Workflow Context

The domain operates as an engine that controls the progression of states.

### 2. Rules Engine (Decision Context)

> Evaluates decisions based on the game's business rules.

**The context must apply rules such as:**
```css
1. value of each card
2. score evaluation
3. soft/hard aces
4. dealer rules (“hit until 17”)
5. win conditions
6. natural blackjack
```
This is a classic archetype:

✔️ Decision Maker / Policy Context

It contains clear and immutable domain policies.

### 3. State Machine (Stateful Context)

> The game maintains a state that evolves according to events.

**The Game or GameTable object is a state machine:**
```bash
- INIT
- PLAYER_TURN
- DEALER_TURN
- FINISHED
```
**The state determines:**
```css
1. which commands are allowed
2. which rules to apply
3. which events to generate
```
Archetype:

✔️ Stateful Domain / State Machine

### 4. Transactional Consistency Context
> It must guarantee the internal consistency of the aggregate.

**Examples:**
```
- Do not allow drawing a card when the game has ended
- Do not allow two consecutive actions by the dealer
- Do not deal inconsistent cards
```
Ensure valid scores

Archetype:

✔️ Consistency Boundary

The Game context acts as a strong aggregate root.

### 5. Event Source / Domain Event Producer

> The game generates events that are relevant to other contexts.

**Typical events:**
```css
- GameFinished
- GameWon
```
Archetype:

✔️ Domain Event Producer

But it does NOT consume external events (Posture: upstream). 

### 6. Simulation Context (opcional)

> Simulations for AI or statistics.

The same engine can simulate thousands of games offline.
Optional archetype:

✔️ Simulation / Batch Processing

## Inbound Communication
### Messages

| Type                            | Description                                                                     | Example                                                                                                      |
| ------------------------------- | ------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------ |
| **Command (Action Request)**    | Requests the context to perform an operation that changes its state             | `StartGameCommand` (starts a game), `PlayerHitCommand` (draw a card), `PlayerStandCommand` (stand)           |
| **Query (Information Request)** | Requests information without changing the state                                 | `GetGameStateQuery` (get current game state), `GetPlayerHandQuery`                                           |
| **Event (Event Notification)**  | Notifies that something happened in another context, useful for async reactions | `PlayerRegisteredEvent` (player was created), `WalletUpdatedEvent` (balance updated, if it affects the game) |

### Collaborators

Collaborators that send incoming messages to the Game Context can be:

| Collaborator                  | Type                    | Message Sent                                                                  |
| ----------------------------- | ----------------------- | ----------------------------------------------------------------------------- |
| **Player UI (Web or Mobile)** | Direct User             | `StartGameCommand`, `PlayerHitCommand`, `PlayerStandCommand`                  |
| **Player Context**            | Another Bounded Context | `PlayerRegisteredEvent`                                                       |
| **Wallet Context**            | Another Bounded Context | `WalletUpdatedEvent` (if balance needs to be verified before starting a game) |

### Relationship Type

- Player UI → Game: Client / provider (UI sends commands; Game processes and returns state).
- Player Context → Game: Upstream → Game consumes events from the Player Context.
 -Wallet Context → Game: Upstream → Game reacts to financial events if betting rules apply.
- 
### Organizing Collaborators in Lanes
```bash
+---------------------+       +------------------------+
| Player UI           |       | Commands: StartGame,   |
|                     | ----> | PlayerHit, PlayerStand |
+---------------------+       +------------------------+

+---------------------+       +------------------------+
| Player Context      |       | Events:                |
|                     | ----> |       PlayerRegistered |
+---------------------+       +------------------------+

+---------------------+       +------------------------+
| Wallet Context      |       | Events: WalletUpdated  |
|                     | ----> |                        |
+---------------------+       +------------------------+

+---------------------+       +------------------------+
| Game Scheduler      |       | Commands: StartGame    |
|                     | ----> |                        |
+---------------------+       +------------------------+
```
## Outbound Communication
### Outbound Messages
The Game Context can send three types of messages:

#### 1. Domain Events (Notifications of something that happened within the Game)
> The Game Context is a natural event producer whenever the game state changes.

| Event                   | When It Is Sent                            | Potential Consumers   |
| ----------------------- | ------------------------------------------ | --------------------- |
| **GameStartedEvent**    | When a game starts                         | Analytics, Rewards    |
| **CardDealtEvent**      | When a card is dealt (to player or dealer) | Observers, Anti-cheat |
| **PlayerHitEvent**      | When the player hits                       | Analytics             |
| **PlayerStandEvent**    | When the player stands                     | Analytics             |
| **DealerFinishedEvent** | When the dealer finishes their turn        | Analytics             |
| **GameWonEvent**        | When the player wins                       | Rewards, Ranking      |
| **GameLostEvent**       | When the player loses                      | Ranking, Wallet       |
| **GameTiedEvent**       | When the game is tied                      | Analytics             |

> These events do not change the state in other contexts; they only notify.
---

#### 2. Commands (Orders that the Game sends to other contexts)

> This occurs when the Game needs another context to perform a specific action in response to the game.

| Command                          | Reason                                                  | Target Context  |
| -------------------------------- | ------------------------------------------------------- | --------------- |
| **AwardRewardCommand**           | Player won and should be awarded an NFT or other reward | Rewards Context |
| **UpdatePlayerRankingCommand**   | Update the player’s ranking                             | Ranking Context |
| **ChargeEntryFeeCommand** (opt.) | Charge the player’s entry fee                           | Wallet Context  |

---

#### 3. Queries (Information Requests)

> Occasionally, the Game may need external information.

| Query                      | Reason                                  | Context        |
| -------------------------- | --------------------------------------- | -------------- |
| **GetPlayerProfileQuery**  | Retrieve name, avatar, preferences      | Player Context |
| **GetBalanceQuery** (opt.) | Validate balance before starting a game | Wallet Context |

### Outbound Collaborators (Message Destinations)
Collaborators that receive outbound messages from the Game are:

| Collaborator              | Type             | Message Received                                              |
| ------------------------- | ---------------- | ------------------------------------------------------------- |
| **Rewards Context**       | Downstream       | `AwardRewardCommand`, `GameWonEvent`                          |
| **Ranking Context**       | Downstream       | `UpdatePlayerRankingCommand`, `GameWonEvent`, `GameLostEvent` |
| **Analytics Context**     | Passive Observer | All game domain events                                        |
| **Wallet Context** (opt.) | Downstream       | `ChargeEntryFeeCommand`, `GameWonEvent`                       |
| **Player Context**        | Consulted        | Information queries about the player                          |

### Relationship Type (Type of relationship between contexts)
- Game → Rewards: Downstream (Rewards depends on events generated by the Game)
- Game → Ranking: Downstream (Ranking updates based on game results)
- Game → Analytics: Broadcast (read-only listener)
- Game → Wallet: Downstream (financial actions)
- Game → Player: Customer/Supplier (informational queries)

### Outbound Lanes (diagram)

```bash
+-------------------------+      +------------------------+
| Game Context            | ---> | Rewards Context         |
| (events + commands)     |      | AwardRewardCommand     |
+-------------------------+      +------------------------+

+-------------------------+      +------------------------+
| Game Context            | ---> | Ranking Context         |
| GameWonEvent            |      | UpdatePlayerRankingCmd |
+-------------------------+      +------------------------+

+-------------------------+      +------------------------+
| Game Context            | ---> | Analytics Context       |
| All domain events       |      | Passive listener       |
+-------------------------+      +------------------------+

+-------------------------+      +------------------------+
| Game Context            | ---> | Wallet Context          |
| ChargeEntryFeeCmd       |      | Balance operations     |
+-------------------------+      +------------------------+

+-------------------------+      +------------------------+
| Game Context            | ---> | Player Context          |
| Queries (profile)       |      | Provides info          |
+-------------------------+      +------------------------+

```

## Ubiquitous Language — Game Context (Blackjack)

These are the terms that should be used consistently throughout the model, code, and conversations.

Each term has a unique and precise meaning within the context of the game.

### 🎴 Core Cards & Hands
`Card`
- A playing card with a rank and suit.
- Only the rank influences Blackjack scoring.

`Rank`
- The value category of the card (Ace, 2–10, Jack, Queen, King).

`Suit`
- Clubs, Diamonds, Hearts, Spades.
- Does not affect scoring.

`Hand`
- The set of cards held by a participant (player or dealer).
- A hand can grow during the game.

`Score`
- The numerical value of a hand applying Blackjack rules.
- Aces can count as 1 or 11.

`Soft Hand`
- A hand containing an Ace counted as 11.

`Hard Hand`
- A hand where Aces count as 1.

---

### 🧍‍♂️ Actors
`Player`
- The human or system participant playing against the dealer.

`Dealer`
- The house.
- Follows fixed rules: must hit until reaching 17 or more.

---

### 🎮 Game Mechanics
`Game`
- A full Blackjack round with one player and one dealer.

---

`Game State`
- The current phase:

`INIT`

`PLAYER_TURN`

`DEALER_TURN`

`FINISHED`

---

`Hit`
- Player draws a card.
---
`Stand`
- Player ends their turn.
---
`Bust`
- Hand exceeds 21 points and loses immediately.
---
`Blackjack`
- Initial hand = Ace + 10-value card.
---
### Outcome
- The final result:

`PLAYER_WINS`

`DEALER_WINS`

`TIE`

## 📜 Business Decisions — Policies & Rules

Here we define the business rules, not technical concepts.

These are domain decisions for Blackjack.

### Card and Hand Rules

**➤ Card Value Rules**
```bash
- 2–10 → face value

- J, Q, K → 10 points

- Ace → 1 or 11, whichever favors the hand
```` 

**➤ Score Calculation Policy**
```bash
- Use the highest valid score ≤ 21

- Aces must dynamically switch between 1 and 11
````

### Player Rules

**➤ Player may:**
```bash
- Hit any number of times until bust

- Stand at any time

- Automatically loses on bust
```

**➤ Player cannot:**
```bash
- Hit after standing

- Act out of turn
```
### Dealer Rules

**➤ Dealer must:**
```bash
- Reveal hidden card after player stands

- Hit until score ≥ 17

- Stand at 17 or more (including soft 17, depending on variant)
```

**➤ Dealer cannot:**
```bash
- Act before player finishes

- Choose strategy — rules are fixed
```

### Game Flow Rules
**➤ Game Start**
```bash
- Deal 2 cards to player, 2 to dealer

- Dealer shows one card, hides the other

- If player has blackjack:
     - If dealer also → tie
     - Else → player wins immediately
```

**➤ Turn Order**
```bash
- Player

- Dealer

- Evaluate outcome
```

**➤ Final Outcome Rules**
```bash
- Highest score ≤ 21 wins

- Equal scores → tie

- Bust always loses

- Blackjack beats all non-blackjack 21s
```

### Event Production Rules (if there are microservices)
**➤ Game must emit:**

`GameStarted`

`CardDealt`

`PlayerHit`

`PlayerStand`

`DealerFinished`

`GameWon`

`GameLost`

`GameTied`

> It does not perform external actions directly; only events and commands.

---

## Resume:

### Blackjack Game Context Canvas

**Name:** Blackjack Game

**Purpose:**
Provide a fair, transparent, and fully automated engine to manage Blackjack games, handling player and dealer actions, enforcing the rules of the game, and generating reliable game outcomes.

**Strategic Classification:**

| Domain | Business             | Evolution    |
| ------ | -------------------- | ------------ |
| Core   | Engagement / Revenue | Custom Build |

**Domain Roles:**

| Role Types        |
| ----------------- |
| Execution Context |

---

### Inbound Communication

| Collaborator              | Messages                                                     |
| ------------------------- | ------------------------------------------------------------ |
| Player UI (Web or Mobile) | `StartGameCommand`, `PlayerHitCommand`, `PlayerStandCommand` |
| Player Context            | `PlayerRegisteredEvent`                                      |
| Wallet Context            | `WalletUpdatedEvent` (if needed for validating entry)        |
| Game Scheduler            | `StartGameCommand`                                           |

---

### Outbound Communication

| Messages                   | Collaborator                     |
| -------------------------- | -------------------------------- |
| GameStartedEvent           | Analytics, Rewards Context       |
| CardDealtEvent             | Analytics, Observers, Anti-cheat |
| PlayerHitEvent             | Analytics                        |
| PlayerStandEvent           | Analytics                        |
| DealerFinishedEvent        | Analytics                        |
| GameWonEvent               | Rewards Context, Ranking Context |
| GameLostEvent              | Ranking Context, Wallet Context  |
| GameTiedEvent              | Analytics                        |
| AwardRewardCommand         | Rewards Context                  |
| UpdatePlayerRankingCommand | Ranking Context                  |
| ChargeEntryFeeCommand      | Wallet Context                   |
| GetPlayerProfileQuery      | Player Context                   |
| GetBalanceQuery            | Wallet Context                   |

---

### Ubiquitous Language

| Term       | Meaning                                                |
| ---------- | ------------------------------------------------------ |
| Card       | Playing card with rank and suit (rank affects scoring) |
| Rank       | Value category (Ace, 2-10, J, Q, K)                    |
| Suit       | Clubs, Diamonds, Hearts, Spades (no effect on scoring) |
| Hand       | Set of cards held by player or dealer                  |
| Score      | Numerical value of a hand according to Blackjack rules |
| Soft Hand  | Hand containing an Ace counted as 11                   |
| Hard Hand  | Hand where Aces count as 1                             |
| Player     | Human or system participant                            |
| Dealer     | House, follows fixed rules                             |
| Game       | One complete Blackjack round                           |
| Game State | INIT, PLAYER_TURN, DEALER_TURN, FINISHED               |
| Hit        | Draw a card                                            |
| Stand      | End turn                                               |
| Bust       | Hand exceeds 21, loses immediately                     |
| Blackjack  | Initial hand = Ace + 10-value card                     |
| Outcome    | PLAYER_WINS, DEALER_WINS, TIE                          |

### Business Decisions

| Rule / Policy     | Description                                                                                              |
| ----------------- | -------------------------------------------------------------------------------------------------------- |
| Card Values       | 2-10 face value, J/Q/K = 10, Ace = 1 or 11                                                               |
| Score Calculation | Use highest valid score <= 21, dynamically switch Aces                                                   |
| Player Actions    | Can hit or stand, bust loses automatically, cannot act out of turn                                       |
| Dealer Actions    | Reveal hidden card after player, hit until >=17, cannot choose strategy                                  |
| Game Flow         | Initial deal 2 cards each, resolve blackjack immediately, turn order: player → dealer → evaluate outcome |
| Outcome Rules     | Highest score <=21 wins, equal scores tie, blackjack beats non-blackjack 21s                             |
| Event Production  | Emit GameStarted, CardDealt, PlayerHit, PlayerStand, DealerFinished, GameWon, GameLost, GameTied         |

---

### Verification Metrics

| Metric                           | Description                                               | Source                   |
| -------------------------------- | --------------------------------------------------------- | ------------------------ |
| Number of Game Logic Bugs        | Count of defects in scoring or rule enforcement           | CI/CD, JIRA              |
| Test Coverage of Game Rules      | % of domain logic covered by automated tests              | CI/CD tools              |
| Latency of Game Operations       | Time to process turns, deal cards, resolve outcomes       | Live system monitoring   |
| Cross-Context Issues             | Number of integration issues with Player, Wallet, Rewards | Postmortems, bug reports |
| Event Delivery Success Rate      | % of domain events successfully delivered                 | Event broker monitoring  |
| Player Satisfaction / Engagement | Feedback on fairness, speed, clarity                      | Analytics, surveys       |

### Open Questions

* Should the Game Context enforce betting rules or delegate to Wallet?
* How should multiple concurrent games be handled (aggregate per game)?
* Soft 17 rule for dealer — required or MVP can use hard 17?
* How to handle delayed/offline event processing for Rewards or Analytics?
* Should the Game Context expose synchronous APIs to the UI or rely on event-driven updates?
* What happens if event delivery fails downstream — retry, or mark game inconsistent?
