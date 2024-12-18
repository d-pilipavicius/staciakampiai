export class MissingAuthError extends Error {
  constructor(message: string) {
    super(message);
    Object.setPrototypeOf(this, MissingAuthError.prototype);
  }
}