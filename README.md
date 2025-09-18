# Karoo MockLocation Extension

![GitHub License](https://img.shields.io/github/license/timklge/karoo-mocklocation)

Simple karoo extension to mock gps locations. For development / debugging purposes only.
Locations can be set via adb broadcast intents.

Compatible with Karoo 3 devices.

## Prerequisites

1. Enable Developer Options on your Karoo
2. Enable USB Debugging
3. Connect your device to a computer with ADB installed
4. Install and run the Karoo Mock Location Extension
5. Set the Karoo Mock Location Extension as the mock location app in Developer Options

## Usage

### Basic Mock Location

To set a basic mock location with just latitude and longitude:

```bash
adb shell am broadcast -a de.timklge.karoomocklocation.SET_MOCK_LOCATION \
  --ef latitude 52.520008 \
  --ef longitude 13.404954
```

### Complete Mock Location

To set a mock location with all parameters:

```bash
adb shell am broadcast -a de.timklge.karoomocklocation.SET_MOCK_LOCATION \
  --ef latitude 52.520008 \
  --ef longitude 13.404954 \
  --ef accuracy 5.0 \
  --ef speed 13.89 \
  --ef bearing 90.0 \
  --ef altitude 100.0
```

## Parameters

| Parameter | Type | Description | Default | Required |
|-----------|------|-------------|---------|----------|
| `latitude` | double | Latitude in decimal degrees | 0.0 | Yes |
| `longitude` | double | Longitude in decimal degrees | 0.0 | Yes |
| `accuracy` | float | GPS accuracy in meters | 5.0 | No |
| `speed` | float | Speed in meters per second | 0.0 | No |
| `bearing` | float | Bearing in degrees (0-360) | 0.0 | No |
| `altitude` | double | Altitude in meters | 0.0 | No |

## Examples

### Static Location (Berlin, Germany)

```bash
adb shell am broadcast -a de.timklge.karoomocklocation.SET_MOCK_LOCATION \
  --ed latitude 52.520008 \
  --ed longitude 13.404954 \
  --ef accuracy 3.0
```

### Moving Location with Speed and Bearing

```bash
# Simulate cycling at 20 km/h heading northeast
adb shell am broadcast -a de.timklge.karoomocklocation.SET_MOCK_LOCATION \
  --ed latitude 52.520008 \
  --ed longitude 13.404954 \
  --ef accuracy 5.0 \
  --ef speed 5.56 \
  --ef bearing 45.0
```

## Notes

- Both latitude and longitude must be provided and non-zero
- Speed is in meters per second (multiply km/h by 0.278 to convert)
- Bearing is in degrees where 0° = North, 90° = East, 180° = South, 270° = West
- The mock location will be set immediately when the broadcast is received
- You can check the device logs for confirmation: `adb logcat | grep MockLocation`

## Troubleshooting

### Check if the broadcast is received:
```bash
adb logcat | grep "MockLocationReceiver"
```

### Verify the extension is running:
```bash
adb logcat | grep "karoo-mocklocation"
```

## Credits

- Icon is `location` by [boxicons.com](https://boxicons.com) (MIT-licensed).

## Links

[karoo-ext source](https://github.com/hammerheadnav/karoo-ext)
