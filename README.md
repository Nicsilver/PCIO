# PCIO

Turn your Android phone into a wireless mouse and keyboard for your PC over your local network.

PCIO has two parts that talk to each other over Wi-Fi:

- **PCIO app** (Android) - the touchpad and keyboard you hold in your hand.
- **PCIO server** (Windows/Java) - a small program that runs on your PC and applies the mouse and keyboard actions the app sends.

The app finds the server automatically on your network, so there is no IP address to type in.

## Download

- **PC server:** grab the latest `PCIO_Server.zip` from the [Releases page](https://github.com/Nicsilver/PCIO/releases/latest) and unzip it anywhere.
- **Android app:** [Google Play listing](https://play.google.com/store/apps/details?id=dk.nicsilver.pcio) *(link becomes live again once the updated build is published)*, or build the APK yourself (see below).

## Getting started

1. Make sure your phone and PC are on the **same Wi-Fi network**.
2. On the PC, unzip the server download and run `StartServer.bat` (needs [Java](https://www.java.com/download/) installed). A tray icon appears while it is running.
3. Open the PCIO app on your phone. It searches the network and lists any PC running the server.
4. Tap your PC to connect. The touchpad controls the mouse; the keyboard button opens text and media key input.

## How it works

Discovery and control run over UDP on these ports:

| Purpose | Port |
| --- | --- |
| App broadcast (find servers) | 47600 |
| PC server listener (default) | 47601 |
| App port listener | 47602 |

The app broadcasts on the network, the server answers with its name, and mouse and keyboard events are streamed to the server, which replays them on the PC using `java.awt.Robot`.

There is a longer write-up of the design decisions (where the touch calculations live, model vs. view split) in [`Documents/Documentation.txt`](Documents/Documentation.txt).

## Repository layout

```
PCIO/
  PCIO_App/       Android app (Gradle)
  PCIO_Server/    PC server (plain Java)
  Documents/      Icons, launch4j config, design notes
```

## Building from source

### Android app

Requires JDK 17 and the Android SDK (compile/target SDK 35).

```
cd PCIO_App
./gradlew assembleRelease        # APK
./gradlew bundleRelease          # AAB for Google Play
```

Create a `local.properties` in `PCIO_App/` pointing at your SDK (use forward slashes):

```
sdk.dir=C:/Android/android-sdk
```

Signing for a Play release is read from a `keystore.properties` file that is intentionally not committed. See [`RELEASING.md`](RELEASING.md).

### PC server

Plain Java, no external dependencies. With JDK 17:

```
cd PCIO_Server
javac -d out $(find src/main/java -name "*.java")
cp -r src/main/resources/* out/
jar cfe PCIO_Server.jar dk.nicsilver.pcioServer.Main -C out .
```

Run it with `java -jar PCIO_Server.jar`.

## Privacy

PCIO collects no data. It has no servers or accounts of its own and talks only to the
companion server on your own PC, over your own network. See the
[privacy policy](https://nicsilver.github.io/PCIO/privacy.html).

## License

[MIT](LICENSE)
