import { IMicroscope } from 'app/shared/model/microscope.model';

export interface IRock {
  id?: number;
  name?: string;
  desc?: string;
  picturePath?: string;
  microscopes?: IMicroscope[];
}

export class Rock implements IRock {
  constructor(
    public id?: number,
    public name?: string,
    public desc?: string,
    public picturePath?: string,
    public microscopes?: IMicroscope[]
  ) {}
}
