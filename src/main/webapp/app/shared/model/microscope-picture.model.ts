import { IMicroscope } from 'app/shared/model/microscope.model';

export interface IMicroscopePicture {
  id?: number;
  path?: string;
  name?: string;
  desc?: string;
  order?: number;
  microscope?: IMicroscope;
}

export class MicroscopePicture implements IMicroscopePicture {
  constructor(
    public id?: number,
    public path?: string,
    public name?: string,
    public desc?: string,
    public order?: number,
    public microscope?: IMicroscope
  ) {}
}
