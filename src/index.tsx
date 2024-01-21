import { NativeModules } from 'react-native';

const LINKING_ERROR =
  `The package '@kevinwaelkens/react-native-data-store' doesn't seem to be linked. Make sure: \n\n` +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const DataStoreModule = NativeModules.DataStore;

if (!DataStoreModule) {
  throw new Error(LINKING_ERROR);
}

export function readDataStoreValue(key: string): Promise<string | null> {
  return DataStoreModule.readDataStoreValue(key);
}

export function writeDataStoreValue(
  key: string,
  value: string
): Promise<string> {
  return DataStoreModule.writeDataStoreValue(key, value);
}

export function clearDataStoreValue(key: string): Promise<string> {
  return DataStoreModule.clearDataStoreValue(key);
}

export function clearDataStore(): Promise<string> {
  return DataStoreModule.clearDataStore();
}
