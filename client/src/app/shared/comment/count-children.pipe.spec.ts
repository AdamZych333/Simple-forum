import { CountChildrenPipe } from './count-children.pipe';

describe('CountChildrenPipe', () => {
  it('create an instance', () => {
    const pipe = new CountChildrenPipe();
    expect(pipe).toBeTruthy();
  });
});
