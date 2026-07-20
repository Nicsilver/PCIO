# Releasing PCIO

The PC server used to be uploaded to anonfiles and linked from the Play listing. That
host is gone and the link is dead. Releases now live on GitHub instead.

## 1. Build the PC server

Plain Java, JDK 17, no dependencies:

```
cd PCIO_Server
javac -d out $(find src/main/java -name "*.java")
cp -r src/main/resources/* out/
jar cfe PCIO_Server.jar dk.nicsilver.pcioServer.Main -C out .
```

Bundle it for users:

```
PCIO_Server.zip
  PCIO_Server.jar
  StartServer.bat   (contains: java -jar PCIO_Server.jar)
```

Attach `PCIO_Server.zip` to the GitHub release. The README's download link always points at
`releases/latest`, so it never needs editing.

## 2. Build the signed Android bundle

Signing credentials are read from `PCIO_App/keystore.properties`, which is git-ignored.
Create it from the example and fill in the real values:

```
storeFile=../Documents/PCIO_KEY.jks
storePassword=YOUR_STORE_PASSWORD
keyAlias=YOUR_KEY_ALIAS
keyPassword=YOUR_KEY_PASSWORD
```

Then:

```
cd PCIO_App
./gradlew bundleRelease
```

The signed `.aab` lands in `app/build/outputs/bundle/release/`.

## 3. Publish

- Upload the `.aab` to the Google Play Console (see notes in the project on the target API
  level requirement and the inactivity removal).
- Create a GitHub release, tag it (e.g. `v1.1`), and attach `PCIO_Server.zip`.

> The keystore `Documents/PCIO_KEY.jks` is the app signing/upload key. Keep a backup. It is
> git-ignored and must never be committed or shared.
