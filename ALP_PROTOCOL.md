# Arcanum Link Protocol (ALP v2.0) Specification

## Payload Schema (`ALPMessage`)
```json
{
  "protocol": "ALP",
  "version": "2.0",
  "messageId": "msg_1784912000000",
  "senderId": "player_hero_01",
  "timestamp": 1784912000000,
  "payloadType": "card_transfer",
  "payload": {
    "cardId": "dragon_flame_01",
    "title": "Ancient Flame Dragon",
    "element": "Fire",
    "attack": 12,
    "defense": 8,
    "rarity": "Legendary"
  }
}
```

## Transport Encodings
- **Universal Link URL**: `arcanum://link?data=<Base64_Encoded_ALPMessage>`
- **QR Payload Format**: Standard Base64 encoded ALP string.
