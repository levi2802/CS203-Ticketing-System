import { render, screen } from '@testing-library/react';
import SeatApp from './SeatApp';

test('renders learn react link', () => {
  render(<App />);
  const linkElement = screen.getByText(/learn react/i);
  expect(linkElement).toBeInTheDocument();
});
