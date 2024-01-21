# react-native-data-store

A module for data and file storage on Android.

## Installation

```sh
yarn add react-native-data-store
```

```sh
npm install react-native-data-store
```

## Usage

```ts
import { writeDataStoreValue } from 'react-native-data-store';

if (Platform.OS === 'android') {
  try {
    await writeDataStoreValue('deviceToken', 'Bearer: ey...');
  } catch (error) {
    console.error(error)
  }
}
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
